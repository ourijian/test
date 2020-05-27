package com.ourijian.startschool.bean;

import java.util.List;

public class AnnouncementBean {

    /**
     * msg : 成功
     * data : [{"id":19,"title":"demoData","description":"demoData","date":"2020-04-12 17:06:01"}]
     * success : true
     */

    private String msg;
    private boolean success;
    private List<Announcement> data;

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

    public List<Announcement> getData() {
        return data;
    }

    public void setData(List<Announcement> data) {
        this.data = data;
    }

    public static class Announcement {
        /**
         * id : 19
         * title : demoData
         * description : demoData
         * date : 2020-04-12 17:06:01
         */

        private int id;
        private String title;
        private String description;
        private String date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
