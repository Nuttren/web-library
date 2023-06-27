package ru.skypro.lessons.springboot.weblibrary.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "position")

public class Position {

    @Id
    private Long positionId;

    @Column(name = "position_name")
    private String positionName;

    public Position(Long positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public Position() {

    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
