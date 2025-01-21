package ksrve;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@XmlRootElement(name = "AuthHeader")
@NoArgsConstructor
public class AuthHeader {
    private String username;
    private String password;

    @XmlElement
    public String getUsername() {
        return username;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

}