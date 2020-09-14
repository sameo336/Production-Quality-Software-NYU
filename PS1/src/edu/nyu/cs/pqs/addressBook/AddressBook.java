package edu.nyu.cs.pqs.addressBook;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The AddressBook class consists of a list of entries. A user of the library can use it to create
 * an empty Address Book. He/She can also add a new entry to the Address Book, remove an entry,
 * search for an entry, save the address book to a file and read the address book from a file.
 * 
 * @author Anisha Bhatla
 *
 */
public class AddressBook {

  private static final Logger LOGGER = Logger.getLogger(AddressBook.class.getName());

  private List<Entry> listOfAddressEntries;

  /**
   * @return a new list containing the current address entries.
   */
  public List<Entry> getListOfAddressEntries() {
    return new ArrayList<Entry>(listOfAddressEntries);
  }

  /**
   * Sets the current list of address entries
   * 
   * @param listOfAddressEntries
   *          the list of address entries to be set to the current list of address entries
   */
  public void setListOfAddressEntries(List<Entry> listOfAddressEntries) {
    this.listOfAddressEntries = (listOfAddressEntries == null) ? new ArrayList<Entry>()
        : new ArrayList<Entry>(listOfAddressEntries);
  }

  /**
   * Class constructor creating an empty address book.
   */
  public AddressBook() {
    listOfAddressEntries = new ArrayList<Entry>();
  }

  /**
   * Adds an entry to the address book.
   * 
   * @param entry
   *          instance of Entry class.
   * @return true if entry is added to the address book successfully, otherwise false.
   */
  public boolean addEntry(Entry entry) {
    return listOfAddressEntries.add(entry);
  }

  /**
   * Removes an existing entry from the address book.
   * 
   * @param entry
   *          instance of Entry class.
   * @return true if the address book contained the entry and successfully removed it, otherwise
   *         false.
   */
  public boolean removeEntry(Entry entry) {
    return listOfAddressEntries.remove(entry);
  }

  /**
   * Searches for an entry in the address book based on the name, phone number, postal address,
   * email or note.
   * 
   * @param keywordToBeSearched
   *          the string to be searched in the address book.
   * @return the entry instance which contains the string to be searched.
   */
  public Entry searchForEntry(String keywordToBeSearched) {
    if (keywordToBeSearched != null && !keywordToBeSearched.trim().equals("")) {
      StringBuilder entryDetails;
      for (Entry entry : listOfAddressEntries) {
        entryDetails = new StringBuilder();
        entryDetails.append(entry.getName()).append(" ").append(entry.getPhoneNumber()).append(" ")
            .append(entry.getPostalAddress()).append(" ").append(entry.getEmail()).append(" ")
            .append(entry.getNote());
        if (entryDetails.toString().toLowerCase().contains(keywordToBeSearched.toLowerCase())) {
          return entry;
        }
      }
    }
    return null;
  }

  /**
   * Saves the address book to a file.
   * 
   * @param filePath
   *          the path to the file where the address book is to be stored. The file is created if it
   *          does not exist.
   */
  @SuppressWarnings("unchecked")
  public void saveAddressBookToFile(String filePath) {
    JSONArray arrayOfEntries = new JSONArray();
    for (Entry entry : listOfAddressEntries) {
      JSONObject entryObject = new JSONObject();
      entryObject.put("Name", entry.getName());
      entryObject.put("PhoneNumber", entry.getPhoneNumber());
      entryObject.put("PostalAddress", entry.getPostalAddress());
      entryObject.put("Email", entry.getEmail());
      entryObject.put("Note", entry.getNote());
      arrayOfEntries.add(entryObject);
    }
    try {
      FileWriter fileWriter = new FileWriter(filePath);
      fileWriter.write(arrayOfEntries.toJSONString());
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException exception) {
      LOGGER.log(Level.SEVERE, "An exception occured: ", exception);
    }
  }

  /**
   * Reads address book entries from a file and stores them to a new Address Book instance.
   * 
   * @param filePath
   *          the path to the file to be read.
   * @return the Address Book instance containing the entries read from the specified file.
   */
  public AddressBook readAddressBookFromFile(String filePath) {
    AddressBook retreivedAddressBook = new AddressBook();
    JSONParser parser = new JSONParser();
    try {
      JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
      for (Object object : jsonArray) {
        JSONObject jsonObject = (JSONObject) object;
        String retrievedName = (String) jsonObject.get("Name");
        String retreivedPhoneNumber = (String) jsonObject.get("PhoneNumber");
        String retreivedAddress = (String) jsonObject.get("PostalAddress");
        String retreivedEmail = (String) jsonObject.get("Email");
        String retreivedNote = (String) jsonObject.get("Note");
        retreivedAddressBook.listOfAddressEntries
            .add(new Entry.Builder(retreivedPhoneNumber).name(retrievedName)
                .postalAddress(retreivedAddress).email(retreivedEmail).note(retreivedNote).build());
      }
    } catch (IOException | ParseException exception) {
      LOGGER.log(Level.SEVERE, "An exception occured: ", exception);
    }
    return retreivedAddressBook;
  }
}
