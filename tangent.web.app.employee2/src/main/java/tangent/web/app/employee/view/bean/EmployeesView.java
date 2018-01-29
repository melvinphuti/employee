package tangent.web.app.employee.view.bean;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import tangent.model.employee.domain.EmployeeProfile;
import tangent.model.employee.enums.DateRangeEnum;
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
    private EmployeeProfile employeeProfile;
    
    private final String URL_TARGET = "http://staging.tangent.tngnt.co/api";
    private final String API_AUTHENTICATION_TOKEN = "2a3d1af2f3f6d1cddaa3012c1c465fcbdffa3678";
    private EmployeeServiceClient employeeServiceClient;
    private EmployeeListFilter employeeListFilter;
    private Statistics statistics;
     
    @PostConstruct
    public void init() {
        employeeListFilter = new EmployeeListFilter();
        employeeServiceClient = new EmployeeServiceClient(URL_TARGET, API_AUTHENTICATION_TOKEN);
        try {
            employeeList = employeeServiceClient.requestEmployeeList();
            employeeProfile = employeeServiceClient.requestLoggedinUsersProfile(API_AUTHENTICATION_TOKEN);
            statistics = buildStatistics(employeeList);
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
    
    public void filterEmployeeListByBirthDateRange(ValueChangeEvent event){
        try {
            Object newValue = event.getNewValue();
            employeeListFilter.setBirthDateRange((DateRangeEnum) newValue);
            employeeList = requestEmployeeList();
        } catch (EmployeeServiceException ex) {
            String errorMessage = "Technical error occurred. Contact Admin for support.";
            FacesMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
            Logger.getLogger(EmployeesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void filterEmployeeListByStartDate(ValueChangeEvent event){
        try {
            Object newValue = event.getNewValue();
            employeeListFilter.setStartDateRange((DateRangeEnum) newValue);
            employeeList = requestEmployeeList();
        } catch (EmployeeServiceException ex) {
            String errorMessage = "Technical error occurred. Contact Admin for support.";
            FacesMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
            Logger.getLogger(EmployeesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
            
    public void filterEmployeeListByUserId(ValueChangeEvent event){
        try {
            Object newValue = event.getNewValue();
            if(newValue == null){
                newValue = "0";
            }
            String newValueString = (String) newValue;
            employeeListFilter.setUserId(Long.valueOf(newValueString) );
            employeeList = requestEmployeeList();
        } catch (EmployeeServiceException ex) {
            String errorMessage = "Technical error occurred. Contact Admin for support.";
            FacesMessageUtil.addMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
            Logger.getLogger(EmployeesView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EmployeeProfile getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmployeeProfile(EmployeeProfile employeeProfile) {
        this.employeeProfile = employeeProfile;
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
    
    public Map<String, Long> getUserMap(){
        Map<String, Long> userMap = null;
        if(employeeList != null){
            userMap = new HashMap<>();
            for(Employee employee :employeeList){
                if(employee.getUser() != null){
                    String key = employee.getUser().getId() + " - " +employee.getUser().getFirstName() 
                            + " " + employee.getUser().getLastName();
                    userMap.put(key, employee.getUser().getId() );
                }
            }
        }
        return userMap;
    }
    
    private Statistics buildStatistics(List<Employee> employeeList){
        Statistics statistics = null;
        if(employeeList != null && !employeeList.isEmpty() ){
            int numberOfFemales = 0, numberOfMales = 0, numberOfBlackAfricans = 0, numberOfWhites = 0, numberOfColoureds = 0, 
                    numberOfIndianOrAsians = 0, numberOfNoneDominants = 0, numberOfBackEndDevelopers = 0,
                    numberOfFrontEndDevelopers = 0, numberOfSeniorProjectManagers = 0, numberOfJuniorProjectManagers = 0;
            for(Employee employee :employeeList){
                if(GenderEnum.FEMALE.getGenderCode().equals(employee.getGender()) ) ++numberOfFemales; else ++numberOfMales;
                if(employee.getRace().equals(RaceEnum.BLACK_AFRICAN.getRaceCode())) ++numberOfBlackAfricans;
                if(employee.getRace().equals(RaceEnum.WHITE.getRaceCode())) ++numberOfWhites;
                if(employee.getRace().equals(RaceEnum.COLOURED.getRaceCode())) ++numberOfColoureds;
                if(employee.getRace().equals(RaceEnum.INDIAN_OR_ASIAN.getRaceCode())) ++numberOfIndianOrAsians;
                if(employee.getRace().equals(RaceEnum.NONE_DOMINANT.getRaceCode())) ++numberOfNoneDominants;
                
                if(employee.getPosition().getId() == PositionEnum.BACK_END_DEVELOPER.positionCode() ) ++numberOfBackEndDevelopers;
                if(employee.getPosition().getId() == PositionEnum.FRONT_END_DEVELOPER.positionCode() ) ++numberOfFrontEndDevelopers;
                if(employee.getPosition().getId() == PositionEnum.PROJECT_MANAGER_JUNIOR.positionCode() ) ++numberOfJuniorProjectManagers;
                if(employee.getPosition().getId() == PositionEnum.PROJECT_MANAGER_SENIOR.positionCode() ) ++numberOfSeniorProjectManagers;
                
                statistics = new Statistics(numberOfFemales, numberOfMales, numberOfBlackAfricans, numberOfWhites, 
                        numberOfIndianOrAsians, numberOfNoneDominants, numberOfBackEndDevelopers, numberOfFrontEndDevelopers, 
                        numberOfSeniorProjectManagers, numberOfJuniorProjectManagers);
            }
        }
        return statistics;
    }

    public Statistics getStatistics() {
        return statistics;
    }
            
}