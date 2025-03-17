package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.exceptions.AddressMandatoryFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private UUID id;
    private Address validAddress;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        validAddress = new Address(id, "123 Main St", "Springfield", "IL", "62704", "USA");
    }

    @Test
    void shouldThrowExceptionWhenStreetIsNullOrEmpty() {
        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, null, "City", "State", "12345", "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "", "City", "State", "12345", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenCityIsNullOrEmpty() {
        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", null, "State", "12345", "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "", "State", "12345", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenStateIsNullOrEmpty() {
        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", null, "12345", "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "", "12345", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenZipCodeIsNullOrEmpty() {
        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", null, "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", "", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenCountryIsNullOrEmpty() {
        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", "12345", null).validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", "12345", "").validate()
        );
    }

    @Test
    void shouldCreateValidAddress() {
        assertDoesNotThrow(validAddress::validate);

        assertAll("Address fields",
                  () -> assertEquals("123 Main St", validAddress.getStreet()),
                  () -> assertEquals("Springfield", validAddress.getCity()),
                  () -> assertEquals("IL", validAddress.getState()),
                  () -> assertEquals("62704", validAddress.getZipCode()),
                  () -> assertEquals("USA", validAddress.getCountry())
        );
    }

    @Test
    void shouldReturnNewInstanceWithUpdatedValuesUsingCopyWith() {
        Address updatedAddress = validAddress.copyWith("456 Elm St", null, "CA", null, "Canada");

        assertAll("Updated Address fields",
                  () -> assertEquals("456 Elm St", updatedAddress.getStreet()),
                  () -> assertEquals("Springfield", updatedAddress.getCity()),
                  () -> assertEquals("CA", updatedAddress.getState()),
                  () -> assertEquals("62704", updatedAddress.getZipCode()),
                  () -> assertEquals("Canada", updatedAddress.getCountry())
        );
    }
}
