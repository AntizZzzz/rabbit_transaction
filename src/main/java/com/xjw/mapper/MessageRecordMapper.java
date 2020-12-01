package com.xjw.mapper;

import com.xjw.entity.pojo.MessageRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xiejianwei
 */
@Mapper
public interface MessageRecordMapper {

    /**
     * 使用注解版本插入一条记录
     *
     * @param messageRecord
     * @return
     */
    @Insert({"INSERT INTO `cash_server`.`message_record` (`business_id`, `business_type`, `message_id`, `retries_number`, `status_`, `create_time`) VALUES(#{businessId}, #{businessType}, #{messageId}, #{retriesNumber}, #{status}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id_")
    boolean insert(MessageRecord messageRecord);

    /**
     * 修改状态
     *
     * @param messageId
     * @return
     */
    @Update("UPDATE message_record SET status_ = 1 WHERE message_id = #{messageId}")
    boolean update(String messageId);

    /**
     * 修改状态
     *
     * @param messageId
     * @return
     */
    @Update("DELETE FROM message_record WHERE message_id = #{messageId}")
    boolean delete(String messageId);

    /**
     * 可千万别学我这个代码，我是为了偷懒，停停停，别打了
     *
     * @param messageId
     * @return
     */
    @Select("SELECT * FROM message_record WHERE message_id = #{messageId} limit 1")
    MessageRecord find(String messageId);

    /**
     * 可千万别学我这个代码，我是为了偷懒，停停停，别打了
     *
     * @param status
     * @return
     */
    @Select("SELECT * FROM message_record WHERE status_ = #{status}")
    List<MessageRecord> findAll(int status);
}
