package compas.txn_params;

import compas.models.bankoperations.Inquiries.InquiriesRequestData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by CLLSDJACKT013 on 09/06/2018.
 */
public interface InquiriesRequestDataRepository extends CrudRepository<InquiriesRequestData,Long> {
    @Modifying
    @Query("update InquiriesRequestData inquiriesrequestdata set inquiriesrequestdata.status ='S' where inquiriesrequestdata.transId =:transId")
    void updateSuccessfulInquiryRequestData(@Param("transId") String transId);

}
