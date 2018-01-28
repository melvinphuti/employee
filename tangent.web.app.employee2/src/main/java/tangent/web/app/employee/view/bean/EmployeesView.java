package tangent.web.app.employee.view.bean;
 
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import tangent.client.employee.EmployeeServiceClient;
import tangent.model.employee.domain.Employee;
import tangent.model.employee.enums.GenderEnum;
import tangent.model.employee.enums.PositionEnum;
import tangent.model.employee.enums.RaceEnum;
import tangent.model.employee.exception.EmployeeServiceException;
import tangent.tangent.web.libraries.FacesMessageUtil;
 
@ManagedBean(name="dtEmployeesView")
@SessionScoped
public class EmployeesView implements Serializable {
     
    private List<Employee> employeeList;
    private Employee selectedEmployee;
    
    private final String URL_TARGET = "http://staging.tangent.tngnt.co/api";
    private final String API_AUTHENTICATION_TOKEN = "2a3d1af2f3f6d1cddaa3012c1c465fcbdffa3678";
    private EmployeeServiceClient employeeServiceClient;
    private EmployeeListFilter employeeListFilter;
     
    @PostConstruct
    public void init() {
        employeeListFilter = new EmployeeListFilter();
        employeeServiceClient = new EmployeeServiceClient(URL_TARGET, API_AUTHENTICATION_TOKEN);
        try {
            employeeList = employeeServiceClient.requestEmployeeList();
        } catch (EmployeeServiceException ex) {
            String errorMessage = "Technical error occurred. Contact Admin for support.";
            FacesMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
            Logger.getLogger(EmployeesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filterEmployeeList(ValueChangeEvent event){
        try {
            Object newValue = event.getNewValue();
            Object oldValue = event.getOldValue();
            if(newValue instanceof GenderEnum || oldValue instanceof GenderEnum){
                employeeListFilter.setGender((GenderEnum) newValue);
            }else if(newValue instanceof PositionEnum || oldValue instanceof PositionEnum){
                employeeListFilter.setPosition((PositionEnum) newValue);
            }else if(newValue instanceof RaceEnum || oldValue instanceof RaceEnum){
                employeeListFilter.setRace((RaceEnum) newValue);
            }
            employeeList = requestEmployeeList();
        } catch (EmployeeServiceException ex) {
            String errorMessage = "Technical error occurred. Contact Admin for support.";
            FacesMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
            Logger.getLogger(EmployeesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public EmployeeServiceClient getEmployeeServiceClient() {
        return employeeServiceClient;
    }

    public void setEmployeeServiceClient(EmployeeServiceClient employeeServiceClient) {
        this.employeeServiceClient = employeeServiceClient;
    }

    public EmployeeListFilter getEmployeeListFilter() {
        return employeeListFilter;
    }

    public void setEmployeeListFilter(EmployeeListFilter employeeListFilter) {
        this.employeeListFilter = employeeListFilter;
    }
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Employee Selected", ((Employee) event.getObject()).getGithubUser());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public void onRowUnselect(UnselectEvent event) {
//        FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private List<Employee> requestEmployeeList() throws EmployeeServiceException {
        employeeList = employeeServiceClient.requestEmployeeList(
                    employeeListFilter.getRace(),
                    employeeListFilter.getPosition(),
                    employeeListFilter.getStartDateRange(),
                    ((employeeListFilter.getUserId() < 1) ? null : employeeListFilter.getUserId()+""),
                    employeeListFilter.getGender(),
                    employeeListFilter.getBirthDateRange(),
                    employeeListFilter.getEmailContains() );
        return employeeList;
    }
}