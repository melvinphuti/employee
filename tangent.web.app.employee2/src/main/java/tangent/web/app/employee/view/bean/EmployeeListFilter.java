/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tangent.web.app.employee.view.bean;

import java.io.Serializable;
import tangent.model.employee.enums.DateRangeEnum;
import tangent.model.employee.enums.GenderEnum;
import tangent.model.employee.enums.PositionEnum;
import tangent.model.employee.enums.RaceEnum;

/**
 *
 * @author User1
 */
public class EmployeeListFilter implements Serializable{
    private RaceEnum race;
    private PositionEnum position;
    private DateRangeEnum startDateRange;
    private long userId;
    private GenderEnum gender;
    private DateRangeEnum birthDateRange;
    private String emailContains;

    public RaceEnum getRace() {
        return race;
    }

    public void setRace(RaceEnum race) {
        this.race = race;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public DateRangeEnum getStartDateRange() {
        return startDateRange;
    }

    public void setStartDateRange(DateRangeEnum startDateRange) {
        this.startDateRange = startDateRange;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public DateRangeEnum getBirthDateRange() {
        return birthDateRange;
    }

    public void setBirthDateRange(DateRangeEnum birthDateRange) {
        this.birthDateRange = birthDateRange;
    }

    public String getEmailContains() {
        return emailContains;
    }

    public void setEmailContains(String emailContains) {
        this.emailContains = emailContains;
    }
    
    public GenderEnum[] getGenderList(){
        return GenderEnum.values();
    } 
    
    public RaceEnum[] getRaceList(){
        return RaceEnum.values();
    } 
    
    public PositionEnum[] getPositionList(){
        return PositionEnum.values();
    } 
    public DateRangeEnum[] getDateRangeList(){
        return DateRangeEnum.values();
    }
    
}
