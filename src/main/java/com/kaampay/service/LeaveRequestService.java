package com.kaampay.service;

import com.kaampay.entity.LeaveRequest;
import com.kaampay.entity.LeaveRequestStatus;
import com.kaampay.repository.LeaveRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class LeaveRequestService {

    private final LeaveRequestRepository repository;

    public LeaveRequestService(LeaveRequestRepository repository) {
        this.repository = repository;
    }

    public LeaveRequest createRequest(LeaveRequest request) {
        // Explicitly set the timestamp if the request is created as APPROVED from the start
        if (request.getStatus() == LeaveRequestStatus.approved) {
            request.setApprovedAt(LocalDateTime.now());
        }
        return repository.save(request);
    }

    public List<LeaveRequest> getAllRequests() {
        return repository.findAll();
    }

    public LeaveRequest getRequestById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave Request not found with id: " + id));
    }

    public List<LeaveRequest> getRequestsByEmployeeId(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<LeaveRequest> getRequestsByCompanyId(String companyId) {
        return repository.findByCompanyId(companyId);
    }

    @Transactional
    public LeaveRequest updateRequest(String id, Map<String, Object> updates) {
        LeaveRequest existingRequest = getRequestById(id);

        updates.forEach((key, value) -> {
            if (value != null) {
                // Convert key to lowercase to prevent silent failures from minor typos like "Status" vs "status"
                switch (key.toLowerCase()) {
                    case "status":
                        // We use .toLowerCase() here to match the new enum and the database
                        LeaveRequestStatus newStatus = LeaveRequestStatus.valueOf(value.toString().toLowerCase());
                        existingRequest.setStatus(newStatus);

                        // Check against the lowercase enum
                        if (newStatus == LeaveRequestStatus.approved) {
                            existingRequest.setApprovedAt(LocalDateTime.now());
                        } else {
                            existingRequest.setApprovedAt(null);
                        }
                        break;

                    case "reason":
                        existingRequest.setReason(value.toString());
                        break;

                    // --- NEW CASE ADDED HERE ---
                    case "attachments":
                        existingRequest.setAttachments(value.toString());
                        break;

                    case "approvedby": // Lowercase to match the key.toLowerCase() check
                        existingRequest.setApprovedBy(value.toString());
                        break;

                    default:
                        // Ignore unknown fields
                        break;
                }
            }
        });

        return repository.save(existingRequest);
    }

    public void deleteRequest(String id) {
        LeaveRequest request = getRequestById(id);
        repository.delete(request);
    }
}