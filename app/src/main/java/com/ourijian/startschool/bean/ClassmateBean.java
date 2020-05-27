package com.ourijian.startschool.bean;

import java.util.List;

public class ClassmateBean {

    /**
     * msg : 成功
     * data : [{"id":8,"name":"oukey","password":"123","email":"8924","telephone":"15674","department":"旅游管理系","major":"酒店管理","role":"学生"}]
     * success : true
     */

    private String msg;
    private boolean success;
    private List<Student> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Student> getData() {
        return data;
    }

    public void setData(List<Student> data) {
        this.data = data;
    }

    public static class Student {
        /**
         * id : 8
         * name : oukey
         * password : 123
         * email : 8924
         * telephone : 15674
         * department : 旅游管理系
         * major : 酒店管理
         * role : 学生
         */

        private int id;
        private String name;
        private String password;
        private String email;
        private String telephone;
        private String department;
        private String major;
        private String role;

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", department='" + department + '\'' +
                    ", major='" + major + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    @Override
    public String toString() {
        return "ClassmateBean{" +
                "msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
