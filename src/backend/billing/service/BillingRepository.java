package backend.billing.service;

import model.Billing;
import model.BillingItem;
import model.Insurance;
import model.Patient;
import util.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillingRepository {

    public void saveBilling(Billing billing, List<BillingItem> items) {
        try {
            String billingQuery = "INSERT INTO billing (patient_id, total, date) VALUES (?, ?, ?)";
            int billingId = MySQL.executeUpdateWithKeys(billingQuery,
                    billing.getPatient().getPatientId(),
                    billing.getTotal(),
                    billing.getDate());

            for (BillingItem item : items) {
                String itemQuery = "INSERT INTO billing_item (billing_id, description, amount) VALUES (?, ?, ?)";
                MySQL.executeUpdate(itemQuery, billingId, item.getDescription(), item.getAmount());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save billing: " + e.getMessage());
        }
    }

    public Billing getBilling(int billingId) {
        try {
            String query = "SELECT b.billing_id, b.patient_id, b.total, b.date, p.name AS patient_name "
                    + "FROM billing b JOIN patient p ON b.patient_id = p.patient_id "
                    + "WHERE b.billing_id = ?";
            ResultSet rs = MySQL.executeQuery(query, billingId);
            if (rs.next()) {
                Billing billing = new Billing();
                billing.setBillingId(rs.getInt("billing_id"));
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("patient_name"));
                billing.setPatient(patient);
                billing.setTotal(rs.getBigDecimal("total"));
                billing.setDate(rs.getObject("date", LocalDate.class));
                rs.close();
                return billing;
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve billing: " + e.getMessage());
        }
    }

    public List<Billing> getAllBillings() {
        try {
            String query = "SELECT b.billing_id, b.patient_id, b.total, b.date, p.name AS patient_name "
                    + "FROM billing b JOIN patient p ON b.patient_id = p.patient_id";
            ResultSet rs = MySQL.executeQuery(query);
            List<Billing> billings = new ArrayList<>();
            while (rs.next()) {
                Billing billing = new Billing();
                billing.setBillingId(rs.getInt("billing_id"));
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("patient_name"));
                billing.setPatient(patient);
                billing.setTotal(rs.getBigDecimal("total"));
                billing.setDate(rs.getObject("date", LocalDate.class));
                billings.add(billing);
            }
            rs.close();
            return billings;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve billings: " + e.getMessage());
        }
    }

    public List<Billing> searchBillings(String query, LocalDate startDate, LocalDate endDate) {
        try {
            String sql = "SELECT b.billing_id, b.patient_id, b.total, b.date, p.name AS patient_name "
                    + "FROM billing b JOIN patient p ON b.patient_id = p.patient_id "
                    + "WHERE (p.name LIKE ? OR b.billing_id = ?)"
                    + (startDate != null && endDate != null ? " AND b.date BETWEEN ? AND ?" : "");
            List<Object> params = new ArrayList<>();
            params.add("%" + query + "%");
            params.add(query);
            if (startDate != null && endDate != null) {
                params.add(startDate);
                params.add(endDate);
            }
            ResultSet rs = MySQL.executeQuery(sql, params.toArray());
            List<Billing> billings = new ArrayList<>();
            while (rs.next()) {
                Billing billing = new Billing();
                billing.setBillingId(rs.getInt("billing_id"));
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("patient_name"));
                billing.setPatient(patient);
                billing.setTotal(rs.getBigDecimal("total"));
                billing.setDate(rs.getObject("date", LocalDate.class));
                billings.add(billing);
            }
            rs.close();
            return billings;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search billings: " + e.getMessage());
        }
    }

    public void saveInsurance(Insurance insurance) {
        try {
            String query = "INSERT INTO insurance (patient_id, provider_name, policy_number, claim_amount, status, description) VALUES (?, ?, ?, ?, ?, ?)";
            MySQL.executeUpdate(query,
                    insurance.getPatient().getPatientId(),
                    insurance.getProviderName(),
                    insurance.getPolicyNumber(),
                    insurance.getClaimAmount(),
                    insurance.getStatus().name(),
                    insurance.getDescription()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save insurance claim: " + e.getMessage());
        }
    }

    public List<Insurance> getInsuranceByBilling(int billingId) {
        try {
            String query = "SELECT i.insurance_id, i.patient_id, i.provider_name, i.policy_number, i.claim_amount, i.status, i.description "
                    + "FROM insurance i JOIN billing b ON i.patient_id = b.patient_id "
                    + "WHERE b.billing_id = ?";
            ResultSet rs = MySQL.executeQuery(query, billingId);
            List<Insurance> insurances = new ArrayList<>();
            while (rs.next()) {
                Insurance insurance = new Insurance(
                        rs.getInt("insurance_id"),
                        new Patient(),
                        rs.getString("provider_name"),
                        rs.getString("policy_number"),
                        rs.getBigDecimal("claim_amount"),
                        Insurance.Status.valueOf(rs.getString("status")),
                        rs.getString("description")
                );
                insurances.add(insurance);
            }
            rs.close();
            return insurances;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve insurance: " + e.getMessage());
        }
    }
}
