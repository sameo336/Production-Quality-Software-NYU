package addressBook;

/**
 * This class is used to make Address entries. It has member variables which
 * define the proper contact of the person. It uses builder class for optional
 * member variables.
 * 
 * @author Sanchit K
 */
public class AddressEntry {
  private final String name;
  private final String postalAddress;
  private final String phoneNo;
  private final String emailAddress;
  private final String note;

  /**
   * Builder class to substitute multiple constructor declarations for optional
   * member variables.
   */
  public static class Builder {

    /**
     * Required parameters
     */
    private final String name;
    private final String postalAddress;

    /**
     * Optional parameters - initialized to default values
     */
    private String phoneNo = "";
    private String emailAddress = "";
    private String note = "";

    public Builder(String name, String postalAddress) {
      this.name = name;
      this.postalAddress = postalAddress;
    }

    public Builder phoneNo(String val) {
      phoneNo = val;
      return this;
    }

    public Builder emailAddress(String val) {
      emailAddress = val;
      return this;
    }

    public Builder note(String val) {
      note = val;
      return this;
    }

    public AddressEntry build() {
      return new AddressEntry(this);
    }

  }

  public String getName() {
    return name;
  }

  public String getPostalAddress() {
    return postalAddress;
  }

  public String getPhoneNo() {
    return phoneNo;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getNote() {
    return note;
  }

  private AddressEntry(Builder builder) {
    name = builder.name;
    postalAddress = builder.postalAddress;
    emailAddress = builder.emailAddress;
    note = builder.note;
    phoneNo = builder.phoneNo;
  }

  /**
   * Overridden ToString method to display the Address entry in the proper
   * format.
   */
  @Override
  public String toString() {
    return ("Name: " + this.name + "	PostalAddress: " + this.postalAddress
        + "	PhoneNo: " + this.phoneNo + "	EmailAddres: " + this.emailAddress
        + "	Note: " + this.note);
  }

  /**
   * Overridden hashCode method to generate the desired hashCode based on the
   * values of the entry.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + (name.hashCode());
    result = prime * result + ((note == null) ? 0 : note.hashCode());
    result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
    result = prime * result + (postalAddress.hashCode());
    return result;
  }

  /**
   * Overridden equals method to check for equality of two address entries.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof AddressEntry)) {
      return false;
    }
    AddressEntry other = (AddressEntry) obj;
    if (emailAddress == null) {
      if (other.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(other.emailAddress)) {
      return false;
    }
    if (!name.equals(other.name)) {
      return false;
    }
    if (note == null) {
      if (other.note != null) {
        return false;
      }
    } else if (!note.equals(other.note)) {
      return false;
    }
    if (phoneNo == null) {
      if (other.phoneNo != null) {
        return false;
      }
    } else if (!phoneNo.equals(other.phoneNo)) {
      return false;
    }
    if (!postalAddress.equals(other.postalAddress)) {
      return false;
    }
    return true;
  }

}