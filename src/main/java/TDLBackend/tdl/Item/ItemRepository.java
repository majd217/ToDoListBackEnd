package TDLBackend.tdl.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO item (label,storeid,checked) values(:#{#item.label}, :#{#item.storeid}, :#{#item.checked})", nativeQuery = true)
    void addItem(@Param("item") Item item);


    @Modifying
    @Transactional
    @Query(value="delete from item it where it.id in ?1", nativeQuery = true)
    void deleteitemswithids(List<Integer> itemIDs);
    
    
    @Modifying
    @Transactional
    @Query(value="update item it set checked= :checked where it.id =:id",nativeQuery = true)
    void updatecheckedValue(@Param("id") int id, @Param("checked") boolean checked);
}
