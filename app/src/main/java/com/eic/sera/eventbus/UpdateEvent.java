package com.eic.sera.eventbus;

public class UpdateEvent {




    public static class UpdateStatus {

        private String Humid;
        private String Temp;

        public UpdateStatus(String humid, String temp) {
            Humid = humid;
            Temp = temp;
        }

        public String getHumid() {
            return Humid;
        }

        public void setHumid(String humid) {
            Humid = humid;
        }

        public String getTemp() {
            return Temp;
        }

        public void setTemp(String temp) {
            Temp = temp;
        }
    }


}
