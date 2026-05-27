package com.wcc.hospital.repository;

import com.wcc.hospital.model.Patient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory repository seeded with 10 realistic hospital patients.
 */
@Repository
public class PatientRepository {

    private final Map<Long, Patient> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(11);

    public PatientRepository() {
        seed();
    }

    public List<Patient> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Patient> findByNationalId(String nationalId) {
        return store.values().stream()
                .filter(p -> p.getNationalId().equals(nationalId))
                .findFirst();
    }

    public List<Patient> findByWard(String ward) {
        return store.values().stream()
                .filter(p -> ward.equalsIgnoreCase(p.getWard()))
                .toList();
    }

    public List<Patient> findByAdmissionStatus(String status) {
        return store.values().stream()
                .filter(p -> status.equalsIgnoreCase(p.getAdmissionStatus()))
                .toList();
    }

    public Patient save(Patient patient) {
        if (patient.getId() == null) {
            patient.setId(idSequence.getAndIncrement());
        }
        store.put(patient.getId(), patient);
        return patient;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    private void seed() {
        List<Patient> patients = List.of(
                Patient.builder().id(1L).firstName("Sarah").lastName("Connor")
                        .nationalId("NHS-1000000001").dateOfBirth(LocalDate.of(1985, 6, 15))
                        .gender("FEMALE").bloodType("O+").phoneNumber("+44 7700 900001")
                        .email("sarah.connor@email.com").address("12 Future Road, London, E1 6RF")
                        .ward("Cardiology").admissionStatus("ADMITTED").admissionDate(LocalDate.of(2024, 3, 1)).build(),

                Patient.builder().id(2L).firstName("James").lastName("Whitfield")
                        .nationalId("NHS-1000000002").dateOfBirth(LocalDate.of(1942, 11, 3))
                        .gender("MALE").bloodType("A+").phoneNumber("+44 7700 900002")
                        .email("j.whitfield@email.com").address("5 Oak Avenue, Manchester, M1 2AB")
                        .ward("Geriatrics").admissionStatus("ADMITTED").admissionDate(LocalDate.of(2024, 2, 14)).build(),

                Patient.builder().id(3L).firstName("Priya").lastName("Sharma")
                        .nationalId("NHS-1000000003").dateOfBirth(LocalDate.of(1993, 4, 22))
                        .gender("FEMALE").bloodType("B+").phoneNumber("+44 7700 900003")
                        .email("priya.sharma@email.com").address("88 Maple Street, Birmingham, B1 3CD")
                        .ward("Oncology").admissionStatus("OUTPATIENT").admissionDate(LocalDate.of(2024, 1, 9)).build(),

                Patient.builder().id(4L).firstName("David").lastName("Okafor")
                        .nationalId("NHS-1000000004").dateOfBirth(LocalDate.of(1978, 8, 30))
                        .gender("MALE").bloodType("AB-").phoneNumber("+44 7700 900004")
                        .email("d.okafor@email.com").address("3 Birch Lane, Leeds, LS1 4EF")
                        .ward("Neurology").admissionStatus("ADMITTED").admissionDate(LocalDate.of(2024, 3, 10)).build(),

                Patient.builder().id(5L).firstName("Emily").lastName("Hartley")
                        .nationalId("NHS-1000000005").dateOfBirth(LocalDate.of(2001, 12, 5))
                        .gender("FEMALE").bloodType("A-").phoneNumber("+44 7700 900005")
                        .email("emily.hartley@email.com").address("27 Rose Gardens, Bristol, BS1 5GH")
                        .ward("General").admissionStatus("DISCHARGED").admissionDate(LocalDate.of(2024, 2, 28)).build(),

                Patient.builder().id(6L).firstName("Mohammed").lastName("Al-Rashid")
                        .nationalId("NHS-1000000006").dateOfBirth(LocalDate.of(1967, 3, 17))
                        .gender("MALE").bloodType("O-").phoneNumber("+44 7700 900006")
                        .email("m.alrashid@email.com").address("61 Cedar Close, Liverpool, L1 6IJ")
                        .ward("ICU").admissionStatus("ADMITTED").admissionDate(LocalDate.of(2024, 3, 12)).build(),

                Patient.builder().id(7L).firstName("Chloe").lastName("Nguyen")
                        .nationalId("NHS-1000000007").dateOfBirth(LocalDate.of(1990, 9, 8))
                        .gender("FEMALE").bloodType("B-").phoneNumber("+44 7700 900007")
                        .email("chloe.nguyen@email.com").address("14 Elm Road, Sheffield, S1 7KL")
                        .ward("Maternity").admissionStatus("ADMITTED").admissionDate(LocalDate.of(2024, 3, 14)).build(),

                Patient.builder().id(8L).firstName("Robert").lastName("Fleming")
                        .nationalId("NHS-1000000008").dateOfBirth(LocalDate.of(1955, 1, 25))
                        .gender("MALE").bloodType("A+").phoneNumber("+44 7700 900008")
                        .email("r.fleming@email.com").address("9 Willow Way, Edinburgh, EH1 8MN")
                        .ward("Cardiology").admissionStatus("OUTPATIENT").admissionDate(LocalDate.of(2024, 1, 20)).build(),

                Patient.builder().id(9L).firstName("Amara").lastName("Diallo")
                        .nationalId("NHS-1000000009").dateOfBirth(LocalDate.of(2010, 7, 11))
                        .gender("FEMALE").bloodType("O+").phoneNumber("+44 7700 900009")
                        .email("amara.diallo@email.com").address("33 Poplar Drive, Cardiff, CF1 9OP")
                        .ward("Paediatrics").admissionStatus("ADMITTED").admissionDate(LocalDate.of(2024, 3, 5)).build(),

                Patient.builder().id(10L).firstName("Thomas").lastName("Eriksson")
                        .nationalId("NHS-1000000010").dateOfBirth(LocalDate.of(1982, 5, 19))
                        .gender("MALE").bloodType("AB+").phoneNumber("+44 7700 900010")
                        .email("t.eriksson@email.com").address("7 Ash Court, Nottingham, NG1 0QR")
                        .ward("Orthopaedics").admissionStatus("DISCHARGED").admissionDate(LocalDate.of(2024, 2, 1)).build()
        );
        patients.forEach(p -> store.put(p.getId(), p));
    }
}
