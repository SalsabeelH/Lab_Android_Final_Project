package birzeit.edu.labandroidfinalproject;



public class User {

        private int id;
        private String email;
        private String firstName;
        private String lastName;
        private String password;
        private String confirmPassword;
        private String preferredTravelDestinations;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        public String getPreferredTravelDestinations() {
            return preferredTravelDestinations;
        }

        public void setPreferredTravelDestinations(String preferredTravelDestinations) {
            this.preferredTravelDestinations = preferredTravelDestinations;
        }
    }

