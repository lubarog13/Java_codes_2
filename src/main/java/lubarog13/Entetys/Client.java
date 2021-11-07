package lubarog13.Entetys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private Date registrationDate;
    private String email;
    private String phone;
    private String GenderCode;
    private String PhotoPath;
}
