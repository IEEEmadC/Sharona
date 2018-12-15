package com.sharonaapp.sharona.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpResponse {

    @SerializedName("data")
    @Expose
    private SignUp signUp;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userMessage")
    @Expose
    private String userMessage;
    @SerializedName("meta")
    @Expose
    private List<Object> meta = null;

    public SignUp getSignUp()
    {
        return signUp;
    }

    public void setData(SignUp data)
    {
        this.signUp = data;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getUserMessage()
    {
        return userMessage;
    }

    public void setUserMessage(String userMessage)
    {
        this.userMessage = userMessage;
    }

    public List<Object> getMeta()
    {
        return meta;
    }

    public void setMeta(List<Object> meta)
    {
        this.meta = meta;
    }

    public class SignUp {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("location")
        @Expose
        private Object location;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("phone_number")
        @Expose
        private Object phoneNumber;
        @SerializedName("address")
        @Expose
        private Object address;
        @SerializedName("national_id")
        @Expose
        private Object nationalId;
        @SerializedName("sheba")
        @Expose
        private String sheba;
        @SerializedName("premium")
        @Expose
        private Object premium;
        @SerializedName("gender")
        @Expose
        private String gender;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public Object getLocation()
        {
            return location;
        }

        public void setLocation(Object location)
        {
            this.location = location;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public Object getPhoneNumber()
        {
            return phoneNumber;
        }

        public void setPhoneNumber(Object phoneNumber)
        {
            this.phoneNumber = phoneNumber;
        }

        public Object getAddress()
        {
            return address;
        }

        public void setAddress(Object address)
        {
            this.address = address;
        }

        public Object getNationalId()
        {
            return nationalId;
        }

        public void setNationalId(Object nationalId)
        {
            this.nationalId = nationalId;
        }

        public String getSheba()
        {
            return sheba;
        }

        public void setSheba(String sheba)
        {
            this.sheba = sheba;
        }

        public Object getPremium()
        {
            return premium;
        }

        public void setPremium(Object premium)
        {
            this.premium = premium;
        }

        public String getGender()
        {
            return gender;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

    }


}


