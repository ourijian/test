package com.ourijian.startschool.bean;

import java.util.List;

public class LeaveWordBean {

    /**
     * msg : 成功
     * data : [{"id":9,"content":"一楼","date":"2020-04-12 10:08:34","user":{"id":3,"name":"jerry","password":"123","email":"gyg@154.bnj","telephone":"15674647473","department":"信息学院","major":"移动应用开发","role":"学生"}}]
     * success : true
     */

    private String msg;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 9
         * content : 一楼
         * date : 2020-04-12 10:08:34
         * user : {"id":3,"name":"jerry","password":"123","email":"gyg@154.bnj","telephone":"15674647473","department":"信息学院","major":"移动应用开发","role":"学生"}
         */

        private int id;
        private String content;
        private String date;
        private UserBean user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
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
    }
}
