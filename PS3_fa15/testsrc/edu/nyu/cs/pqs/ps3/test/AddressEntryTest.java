package edu.nyu.cs.pqs.ps3.test;

import static org.junit.Assert.*;

import java.util.Objects;

import org.junit.Test;

import edu.nyu.cs.pqs.ps1.AddressEntry;

public class AddressEntryTest {

  @Test
  public void testEquals() {
   AddressEntry entry1 =
       new AddressEntry.Builder("TestName1")
   .postalAddress("thane")
   .phoneNumber("0123456789").emailAddress("test@testing.com")
   .note("TestNote").build();
   
   AddressEntry entry2 =
       new AddressEntry.Builder("TestName2")
   .postalAddress("borivali")
   .phoneNumber("01289").emailAddress("test@yahoo.com")
   .note("TestNote").build();
   
   AddressEntry entry3 =
       new AddressEntry.Builder("TestName1")
   .postalAddress("thane")
   .phoneNumber("0123456789").emailAddress("test@testing.com")
   .note("TestNote").build();
   
   AddressEntry entry4 =
       new AddressEntry.Builder("TestName1")
   .postalAddress("thane2")
   .phoneNumber("0123456789").emailAddress("test@testing.com")
   .note("TestNote").build();
   
   AddressEntry entry5 =
       new AddressEntry.Builder("TestName1")
   .postalAddress("thane2")
   .phoneNumber("0129").emailAddress("test@testing.com")
   .note("TestNote").build();
   
   AddressEntry entry6 =
       new AddressEntry.Builder("TestName1")
   .postalAddress("thane2")
   .phoneNumber("0129").emailAddress("Change.com")
   .note("TestNote").build();
   
   AddressEntry entry7 =
       new AddressEntry.Builder("TestName1")
   .postalAddress("thane2")
   .phoneNumber("0129").emailAddress("Change.com")
   .note("Change").build();
   
   AddressEntry entry8 = null;
   
   String s= "Sanchit";
   
   assertFalse(entry1.equals(entry2));
   assertTrue(entry1.equals(entry3));
   assertFalse(entry3.equals(entry4));
   assertFalse(entry4.equals(entry5));
   assertFalse(entry5.equals(entry6));
   assertFalse(entry6.equals(entry7));
   assertFalse(entry6.equals(entry8));
   assertFalse(entry7.equals(s));
  }

  @Test
  public void testHashcode(){
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1")
    .postalAddress("thane")
    .phoneNumber("0123456789").emailAddress("test@testing.com")
    .note("TestNote").build();
    
    int a = Objects.hash(entry1.getName().hashCode(), entry1.getPostalAddress().hashCode(),
        entry1.getPhoneNumber().hashCode(),
        entry1.getEmailAddress().hashCode(), entry1.getNote().hashCode());
    
    assertEquals(a, entry1.hashCode());
  }

  @Test
  public void testToString() {
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1")
    .postalAddress("thane")
    .phoneNumber("0123456789").emailAddress("test@testing.com")
    .note("TestNote").build();
    
    String s ="AddressEntry [name=" + entry1.getName() + ", postalAddress=" + entry1.getPostalAddress() + ", phoneNumber="
        + entry1.getPhoneNumber() + ", emailAddress=" + entry1.getEmailAddress() + ", note=" + entry1.getNote() + "]\n";
    assertEquals(s, entry1.toString());
  }
  
  @Test
  public void testSetters(){
    AddressEntry entry1 =
        new AddressEntry.Builder("TestName1")
    .postalAddress("thane")
    .phoneNumber("0123456789").emailAddress("test@testing.com")
    .note("TestNote").build();
    
    AddressEntry entry2 =
        new AddressEntry.Builder("TestName5").build();
    
    entry2.setEmailAddress("test@testing.com");
    entry2.setName("TestName1");
    entry2.setNote("TestNote");
    entry2.setPhoneNumber("0123456789");
    entry2.setPostalAddress("thane");
    assertTrue(entry1.equals(entry2));
  }

  
  
}
