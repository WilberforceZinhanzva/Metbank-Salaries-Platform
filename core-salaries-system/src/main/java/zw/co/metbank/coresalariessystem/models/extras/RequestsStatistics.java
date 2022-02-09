package zw.co.metbank.coresalariessystem.models.extras;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestsStatistics {
    private Long fileBasedRequests;
    private Long inputBasedRequests;
    private Long initiatedRequests;
    private Long authorizedRequests;
    private Long reviewedRequests;
    private Long approvedRequests;
    private Long remittedRequests;
}
