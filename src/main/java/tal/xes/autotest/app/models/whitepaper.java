package tal.xes.autotest.app.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tal zyz
 * @date 2020.05.09
 * @description 老师白名单列表
* */
@Entity
@Table
public class whitepaper implements Serializable {

    @Id
    @GeneratedValue
    private Long ID;

    @Column(unique = true)
    private String name;

    private String number;

    private Boolean status;

    private Integer page;

    private Integer size;

    private Date createtime;

    private Date updatetime;

    public whitepaper(Long id) {
        ID = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
