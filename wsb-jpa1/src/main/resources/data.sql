INSERT INTO address (city, address_line1, address_line2, postal_code)
VALUES
    ('Warszawa', 'ul. Długa 1', 'Mieszkanie 5', '00-001'),
    ('Kraków', 'ul. Krakowska 2', 'Piętro 2', '31-002');

INSERT INTO doctor (firstname, lastname, telephone_number, email, doctor_number, specialization, address_id)
VALUES
    ('Jan', 'Kowalski', '123456789', 'jan.kowalski@email.com', 'DOC001', 'SURGEON', 1),
    ('Anna', 'Nowak', '987654321', 'anna.nowak@email.com', 'DOC002', 'DERMATOLOGY', 1),
    ('Igor', 'Karlik', '555555555', 'igor.karlik@email.com', 'DOC003', 'ORTHOPEDICS', 2);

INSERT INTO patient (firstname, lastname, telephone_number, email, patient_number, is_insured, date_of_birth, address_id)
VALUES
    ('Piotr', 'Zieliński', '111222333', 'piotr.zielinski@email.com', 'PAT001', false, '1980-05-15', 1),
    ('Ewa', 'Kowalczyk', '444555666', 'ewa.kowalczyk@email.com', 'PAT002', true, '1992-07-25', 2),
    ('Marek', 'Nowicki', '777888999', 'marek.nowicki@email.com', 'PAT003', false, '1985-11-30', 2);

INSERT INTO visit (description, time, patient_id, doctor_id)
VALUES
    ('Regular checkup', '2024-12-01T10:00:00', 1, 1),
    ('Skin examination', '2024-12-02T14:30:00', 2, 2),
    ('Orthopedic consultation', '2024-12-03T09:00:00', 3, 3);

INSERT INTO medical_treatment (description, type, visit_id)
VALUES
    ('Heart transplant', 'USG', 1),
    ('Skin biopsy', 'EKG', 2),
    ('Knee surgery', 'RTG', 3);
