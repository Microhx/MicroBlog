package micro.com.microblog.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by guoli on 2016/9/21.
 *
 * 聊天记录bean
 *
 */

@DatabaseTable(tableName = "tb_msg")
public class MsgBean {

    /**
     * 主键
     */
    @DatabaseField(generatedId = true)
    private int id ;

    /**
     * 聊天的内容
     */
    @DatabaseField(columnName = "content")
    private String content ;

    /**
     * 聊天的时间戳
     */
    @DatabaseField(columnName = "chat_time")
    private long chatTime ;

    /**
     * 发送者ID
     */
    @DatabaseField(columnName = "sender_id")
    private int senderId ;

    /**
     * 接收者ID
     */
    @DatabaseField(columnName = "receive_id")
    private int receivedId ;

    /**
     * 是否删除
     */
    @DatabaseField(columnName = "is_delete")
    private boolean isDeleted ;

    /**
     * 其他备用信息
     */
    @DatabaseField(columnName = "other_info")
    private String otherInfo ;


    public long getChatTime() {
        return chatTime;
    }

    public void setChatTime(long chatTime) {
        this.chatTime = chatTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public int getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(int receivedId) {
        this.receivedId = receivedId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public MsgBean() {
        this.chatTime = chatTime;
    }

    public MsgBean(long chatTime, String content, boolean isDeleted, String otherInfo, int receivedId, int senderId) {
        this.chatTime = chatTime;
        this.content = content;
        this.isDeleted = isDeleted;
        this.otherInfo = otherInfo;
        this.receivedId = receivedId;
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "MsgBean{" +
                "chatTime=" + chatTime +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", senderId=" + senderId +
                ", receivedId=" + receivedId +
                ", isDeleted=" + isDeleted +
                ", otherInfo='" + otherInfo + '\'' +
                '}';
    }
}
