package pl.demoproject.webdownload.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "websites")
@EntityListeners(AuditingEntityListener.class)
public class Website implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String address;

    @Lob
    @Column(name = "htmlFile", columnDefinition="BLOB")
    private byte[] htmlFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(byte[] htmlFile) {
        this.htmlFile = htmlFile;
    }
}