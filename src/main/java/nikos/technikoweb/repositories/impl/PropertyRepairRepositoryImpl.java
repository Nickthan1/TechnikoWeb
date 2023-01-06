/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.repositories.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import nikos.technikoweb.model.Property;
import nikos.technikoweb.model.PropertyRepair;
import nikos.technikoweb.model.User;
import nikos.technikoweb.repository.PropertyRepairRepository;

/**
 *
 * @author legeo
 */
public class PropertyRepairRepositoryImpl extends RepositoryImpl<PropertyRepair, Long> implements PropertyRepairRepository {
   

    @Override
    public List<PropertyRepair> searchDate(LocalDate date) {
        Instant searchDateInstant = localDateToInstant(date);
        Instant searchDateInstantEndOfDay = getNextDayInstant(searchDateInstant);
        return em.createQuery("from PropertyRepair pr where pr.dateOfSubmission BETWEEN :searchDate and :searchDateNextDay", PropertyRepair.class)
                .setParameter("searchDate", searchDateInstant)
                .setParameter("searchDateNextDay", searchDateInstantEndOfDay)
                .getResultList();
    }

    private Instant getNextDayInstant(Instant instant) {
        return instant.plus(1, ChronoUnit.DAYS).minus(1, ChronoUnit.NANOS);

    }

    private Instant localDateToInstant(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    @Override
    public List<PropertyRepair> searchDateRange(LocalDate startDate, LocalDate endDate) {
        Instant startDateInstant = localDateToInstant(startDate);
        Instant endDateInstant = localDateToInstant(endDate);
        Instant endDateInstantEndOfDay = getNextDayInstant(endDateInstant);
        return em.createQuery("from PropertyRepair pr where pr.dateOfSubmission Between pr.dateOfSubmission=:date1 AND pr.dateOfSubmission=:date2", PropertyRepair.class)
                .setParameter("date1", startDateInstant)
                .setParameter("date2", endDateInstantEndOfDay)
                .getResultList();
    }

    @Override
    public List<PropertyRepair> searchOwnerId(int ownerId) {
        return em.createQuery("from PropertyRepair pr join Property p on p.id=pr.property.id join User o on o.id=p.owner.id Where o.id =:ownerId ", PropertyRepair.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }

    @Override
    public int create(PropertyRepair t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        return t.getProperty().getId();
    }

    @Override
    public Optional<PropertyRepair> read(int id) {
        PropertyRepair propertyRepair = em.find(PropertyRepair.class, id);
        return propertyRepair != null && propertyRepair.isActive() ? Optional.of(propertyRepair) : Optional.empty();
    }

    @Override
    public List<PropertyRepair> read() {
        return em.createQuery("from PropertyRepair", PropertyRepair.class).getResultList();
    }

    @Override
    public boolean safeDelete(int id) {
        PropertyRepair propertyRepair = em.find(PropertyRepair.class, id);
        if (propertyRepair == null) {
            return false;
        }
        propertyRepair.setActive(false);
        em.getTransaction().begin();
        em.persist(propertyRepair);
        em.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(PropertyRepair t, int id) {
        PropertyRepair oldPropertyRepair = em.find(PropertyRepair.class, id);
        if (oldPropertyRepair == null || !oldPropertyRepair.isActive()) {
            return false;
        }
        oldPropertyRepair.setRepairType(t.getRepairType());
        oldPropertyRepair.setDescription(t.getDescription());
        oldPropertyRepair.setWorkToBeDone(t.getWorkToBeDone());
        oldPropertyRepair.setProposedStartDate(t.getProposedStartDate());
        oldPropertyRepair.setProposedEndDate(t.getProposedEndDate());
        oldPropertyRepair.setCost(t.getCost());
        oldPropertyRepair.setStatus(t.getStatus());
        oldPropertyRepair.setActualStartDate(t.getActualStartDate());
        oldPropertyRepair.setActualEndDate(t.getActualEndDate());
        updateProperty(oldPropertyRepair);
        return true;
    }

    private void updateProperty(PropertyRepair propertyRepair) {
        em.getTransaction().begin();
        em.persist(propertyRepair);
        em.getTransaction().commit();
    }

    @Override
    public boolean delete(int id) {
        PropertyRepair propertyRepair = em.find(PropertyRepair.class, id);
        Property property = em.find(Property.class, propertyRepair.getProperty().getId());
        User propertyOwner = em.find(User.class, propertyRepair.getProperty().getOwner().getId());
        if (property == null || propertyOwner == null) {
            return false;
        }
        em.getTransaction().begin();
        em.remove(propertyRepair);
        em.getTransaction().commit();
        return true;

    }

    @Override
    public List<PropertyRepair> searchOwnerVat(String vat) {
        return em.createQuery("from PropertyRepair pr join property p on pr.property.id=p.id join Owner o on p.owner.id=o.id where o.vat=:vat ", PropertyRepair.class)
                .setParameter("vat", vat)
                .getResultList();
    }

    @Override
    public List<PropertyRepair> searchByProperty(int propertyId) {
        return em.createQuery("from PropertyRepair pr join Property p on p.id=:propertyId ", PropertyRepair.class)
                .setParameter("propertyId", propertyId)
                .getResultList();
    }

    @Override
    public List<PropertyRepair> searchByPropertyE9(String e9) {
      return em.createQuery("from PropertyRepair pr join Property p on p.id=pr.property.id where p.propertyIdentificationNumber=:e9 ", PropertyRepair.class)
                .setParameter("e9", e9)
                .getResultList();
    }
}
