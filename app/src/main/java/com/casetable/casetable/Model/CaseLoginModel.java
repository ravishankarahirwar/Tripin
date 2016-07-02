package com.casetable.casetable.Model;

public class CaseLoginModel
{
    private String message;

    private String status;

    private Data[] data;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", data = "+data+"]";
    }

    public class Data
    {
        private String Name;

        private String UserType;

        private String UserName;

        private String UserId;

        public String getName ()
        {
            return Name;
        }

        public void setName (String Name)
        {
            this.Name = Name;
        }

        public String getUserType ()
        {
            return UserType;
        }

        public void setUserType (String UserType)
        {
            this.UserType = UserType;
        }

        public String getUserName ()
        {
            return UserName;
        }

        public void setUserName (String UserName)
        {
            this.UserName = UserName;
        }

        public String getUserId ()
        {
            return UserId;
        }

        public void setUserId (String UserId)
        {
            this.UserId = UserId;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Name = "+Name+", UserType = "+UserType+", UserName = "+UserName+", UserId = "+UserId+"]";
        }
    }
}