package com.kai.oasys.pojo;

import java.util.List;

public class SysDepartMent {
    private Integer id;

    private Integer parentdepartmentid;

    private String name;

    private Integer sort;

    private String description;

    private String type;

    private String area;

    private List<SysDepartMent> children;


    public SysDepartMent(Integer id, Integer parentdepartmentid, String name, Integer sort, String description, String type, String area) {
        this.id = id;
        this.parentdepartmentid = parentdepartmentid;
        this.name = name;
        this.sort = sort;
        this.description = description;
        this.type = type;
        this.area = area;
    }

    public SysDepartMent() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentdepartmentid() {
        return parentdepartmentid;
    }

    public void setParentdepartmentid(Integer parentdepartmentid) {
        this.parentdepartmentid = parentdepartmentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public List<SysDepartMent> getChildren() {
        return children;
    }

    public void setChildren(List<SysDepartMent> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentdepartmentid=").append(parentdepartmentid);
        sb.append(", name=").append(name);
        sb.append(", sort=").append(sort);
        sb.append(", description=").append(description);
        sb.append(", type=").append(type);
        sb.append(", area=").append(area);
        sb.append("]");
        return sb.toString();
    }
}