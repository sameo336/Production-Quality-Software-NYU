package edu.nyu.cs.pqs.addressBook;

/**
 * The Entry class is used to create an Entry instance for the AddressBook class. It consists of an
 * entry id, name, phone number, postal address, email and note data members. Entry id and phone
 * number are required data members of an Entry instance. Builder class is used for optional data
 * members.
 * 
 * @author Anisha Bhatla
 *
 */
public class Entry {

  private final int entryId;
  private final String name;
  private final String phoneNumber;
  private final String postalAddress;
  private final String email;
  private final String note;

  /**
   * Builder class to create an Entry instance with provision to initialize optional data members.
   */
  public static class Builder {

    private static int nextUniqueEntryId = 0;
    // Required parameters
    private final int entryId;
    private final String phoneNumber;

    // Optional parameters - initialized to default values
    private String name = "";
    private String postalAddress = "";
    private String email = "";
    private String note = "";

    /**
     * Class constructor initializing the required fields. The entry id is unique and
     * auto-generated.
     * 
     * @param phoneNumber
     *          the string specifying the phone number.
     */
    public Builder(String phoneNumber) {
      this.entryId = ++nextUniqueEntryId;
      this.phoneNumber = phoneNumber;
    }

    /**
     * Initializes the optional member variable name.
     * 
     * @param name
     *          the string specifying the name.
     * @return the current Builder instance.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Initializes the optional member variable postal address.
     * 
     * @param postalAddress
     *          the string specifying the postal address.
     * @return the current Builder instance.
     */
    public Builder postalAddress(String postalAddress) {
      this.postalAddress = postalAddress;
      return this;
    }

    /**
     * Initializes the optional member variable email.
     * 
     * @param email
     *          the string specifying the email.
     * @return the current Builder instance.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Initializes the optional member variable note.
     * 
     * @param note
     *          the string specifying the note.
     * @return the current Builder instance.
     */
    public Builder note(String note) {
      this.note = note;
      return this;
    }

    /**
     * Creates a new Entry instance based on the current Builder instance.
     * 
     * @return the newly-created Entry instance.
     */
    public Entry build() {
      return new Entry(this);
    }
  }

  private Entry(Builder builder) {
    this.entryId = builder.entryId;
    this.name = builder.name;
    this.postalAddress = builder.postalAddress;
    this.phoneNumber = builder.phoneNumber;
    this.email = builder.email;
    this.note = builder.note;
  }

  /**
   * @return the value of the entry id data member.
   */
  public int getEntryId() {
    return entryId;
  }

  /**
   * @return the contents of the name data member.
   */
  public String getName() {
    return name;
  }

  /**
   * @return the contents of the postal address data member.
   */
  public String getPostalAddress() {
    return postalAddress;
  }

  /**
   * @return the contents of the phone number data member.
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the contents of the email data member.
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return the contents of the note data member.
   */
  public String getNote() {
    return note;
  }

  /*
   * Overriden toString method to display the contents of an Entry instance in a proper format.
   */
  @Override
  public String toString() {
    StringBuilder entry = new StringBuilder();
    entry.append("Name: ").append(this.name).append(" , Phone Number: ").append(this.phoneNumber)
        .append(" , Postal Address: ").append(this.postalAddress).append(" , Email: ")
        .append(this.email).append(" , Note: ").append(this.note);
    return entry.toString();
  }

  /*
   * Overriden equals method to compare two Entry instances for equality.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Entry)) {
      return false;
    }
    Entry entry = (Entry) obj;
    return this.entryId == entry.entryId;
  }

  /*
   * Overriden hashCode method to generate the hash code based on the entry id.
   */
  @Override
  public int hashCode() {
    return entryId;
  }
}