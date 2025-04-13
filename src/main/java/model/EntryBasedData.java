package model;

import java.util.List;
/**
 * This is adapter interface, since gene Expressions will use, Entry to store things
 * We can just say that Parsed-> Entry
 * 
 * So that all ParsedData interfaces dont have to work with entires
 */
public interface EntryBasedData extends ParsedData {
    List<Entry> getEntries();
}
