package app.jabafood.cleanarch.domain.entities;

import app.jabafood.cleanarch.domain.exceptions.AddressMandatoryFieldException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    @Test
    void shouldThrowExceptionWhenStreetIsNullOrEmpty() {
        UUID id = UUID.randomUUID();

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, null, "City", "State", "12345", "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "", "City", "State", "12345", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenCityIsNullOrEmpty() {
        UUID id = UUID.randomUUID();

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", null, "State", "12345", "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "", "State", "12345", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenStateIsNullOrEmpty() {
        UUID id = UUID.randomUUID();

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", null, "12345", "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "", "12345", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenZipCodeIsNullOrEmpty() {
        UUID id = UUID.randomUUID();

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", null, "Country").validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", "", "Country").validate()
        );
    }

    @Test
    void shouldThrowExceptionWhenCountryIsNullOrEmpty() {
        UUID id = UUID.randomUUID();

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", "12345", null).validate()
        );

        assertThrows(AddressMandatoryFieldException.class, () ->
                new Address(id, "Street", "City", "State", "12345", "").validate()
        );
    }

    @Test
    void shouldCreateValidAddress() {
        UUID id = UUID.randomUUID();
        Address address = new Address(id, "123 Main St", "Springfield", "IL", "62704", "USA");

        assertDoesNotThrow(address::validate);
        assertEquals("123 Main St", address.getStreet());
        assertEquals("Springfield", address.getCity());
        assertEquals("IL", address.getState());
        assertEquals("62704", address.getZipCode());
        assertEquals("USA", address.getCountry());
    }

    @Test
    void shouldReturnNewInstanceWithUpdatedValuesUsingCopyWith() {
        UUID id = UUID.randomUUID();
        Address address = new Address(id, "123 Main St", "Springfield", "IL", "62704", "USA");

        Address updatedAddress = address.copyWith("456 Elm St", null, "CA", null, "Canada");

        assertEquals("456 Elm St", updatedAddress.getStreet());
        assertEquals("Springfield", updatedAddress.getCity()); // Unchanged
        assertEquals("CA", updatedAddress.getState());
        assertEquals("62704", updatedAddress.getZipCode()); // Unchanged
        assertEquals("Canada", updatedAddress.getCountry());
    }
}

