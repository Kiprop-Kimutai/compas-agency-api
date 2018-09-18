package compas.txn_params;

import compas.models.bankoperations.Inquiries.InquiriesRequest;
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
    @Query("update InquiriesRequestData inquiriesrequestdata set inquiriesrequestdata.status ='S',inquiriesrequestdata.cbs_trans_id = :cbs_trans_id,inquiriesrequestdata.finnacle_response_message = :finnacle_response_message where inquiriesrequestdata.transId =:transId")
    int updateSuccessfulInquiryRequestData(@Param("transId") String transId,@Param("cbs_trans_id")String cbs_trans_id,@Param("finnacle_response_message")String finnacle_response_message);

    @Modifying
    @Query("update InquiriesRequestData inquiriesrequestdata set inquiriesrequestdata.status ='F' ,inquiriesrequestdata.finnacle_response_message =:finnacle_response_message where inquiriesrequestdata.transId =:transId")
    int  updateFailedInquiryRequestData(@Param("transId") String transId,@Param("finnacle_response_message")String finnacle_response_message);

    @Query("select inquiriesRequestData from InquiriesRequestData inquiriesRequestData where inquiriesRequestData.terminal_trans_id = :terminal_trans_id")
    InquiriesRequestData findInquiriesRequestDataByTerminal_trans_id(@Param("terminal_trans_id")String terminal_trans_id);

    @Modifying
    @Query("update InquiriesRequestData  inquiriesRequestData set inquiriesRequestData.status = 'R' where inquiriesRequestData.terminal_trans_id = :terminal_trans_id")
    int updateReversedInquiriesRequestData (@Param("terminal_trans_id")String terminal_trans_id);
}
