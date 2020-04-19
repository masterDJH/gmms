package cn.djh.gmms.domain.vo;
import cn.djh.gmms.domain.Employee;
import org.springframework.web.multipart.MultipartFile;

public class EmployeeVO {
    private Long id;
    private String username;
    private String password;
    // 接收密码修改时传入的新密码
    private String newPassword;
    private String phone;
    private String email;
    private Integer age;

    public EmployeeVO() {
    }
    public EmployeeVO(String newPassword) {
        this.newPassword = newPassword;
    }
    public EmployeeVO(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.age = employee.getAge();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
