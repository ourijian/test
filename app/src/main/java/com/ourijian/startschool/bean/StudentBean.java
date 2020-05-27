package com.ourijian.startschool.bean;

public class StudentBean {

    /**
     * msg : 成功
     * data : {"id":3,"name":"jerry","password":"123","email":"gyg@154.bnj","telephone":"15674647473","department":"信息学院","major":"移动应用开发","role":"学生"}
     * success : true
     */

    private String msg;
    private Student data;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Student getData() {
        return data;
    }

    public void setData(Student data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Student {
        /**
         * id : 3
         * name : jerry
         * password : 123
         * email : gyg@154.bnj
         * telephone : 15674647473
         * department : 信息学院
         * major : 移动应用开发
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

        public Student() {
        }

        public Student(int id, String name, String password, String email, String telephone, String department, String major, String role) {
            this.id = id;
            this.name = name;
            this.password = password;
            this.email = email;
            this.telephone = telephone;
            this.department = department;
            this.major = major;
            this.role = role;
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
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }
}
