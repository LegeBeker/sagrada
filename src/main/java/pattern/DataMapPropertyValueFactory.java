package main.java.pattern;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/*
 * Dit is de DataMapPropertyValueFactory deze class is nodig om de DataMap te kunnen gebruiken in de TableView.
 * Omdat de DataMap een HashMap is en geen model kan je niet zomaar een propertyValueFactory gebruiken.
 * De propertyValueFactory verwacht namelijk een model.
 * Ook ondersteund deze class child properties. Bijv: "player.userName"
 * 
 * Voorbeelden hoe het in de gamesList gebruikt kan worden:
 * Hiermee wordt het id van de game, de username en score van de turnPlayer, de currentRound en de creationDate van de game opgehaald.
 * public ArrayList<DataMap> getGames() {
 *      return DataMapper.createMultiple(Game.getAll(),"id", "turnPlayer{username,score}", "currentRound", "creationDate");
 * }
 * 
 * Hiermee wordt het id uit de DataMap gehaald en in de id column gezet.
 * TableColumn<DataMap, Integer> idCol = new TableColumn<>("Id");
 * idCol.setCellValueFactory(new DataMapPropertyValueFactory<>("id"));
 * 
 * Hiermee wordt de username van de turnPlayer uit de DataMap gehaald en in de username column gezet.
 * TableColumn<DataMap, String> turnPlayerCol = new TableColumn<>("Beurt Speler");
 * turnPlayerCol.setCellValueFactory(new DataMapPropertyValueFactory<>("turnPlayer.username"));
 * 
 */
public class DataMapPropertyValueFactory<S, T> extends PropertyValueFactory<S, T> {
    private String propertyKey;

    public DataMapPropertyValueFactory(final String propertyKey) {
        super(propertyKey);
        this.propertyKey = propertyKey;
    }

    @Override
    public ObservableValue<T> call(final TableColumn.CellDataFeatures<S, T> param) {
        S value = param.getValue();
        if (value instanceof DataMap) {
            DataMap dataMap = (DataMap) value;
            Object propertyValue = getProperty(dataMap, propertyKey);
            @SuppressWarnings("unchecked")
            T castedValue = (T) propertyValue;
            return new SimpleObjectProperty<>(castedValue);
        }
        return new SimpleObjectProperty<>();
    }

    private Object getProperty(final DataMap dataMap, final String propertyKey) {
        String[] propertyParts = propertyKey.split("\\.");
        Object value = dataMap;
        for (String part : propertyParts) {
            if (value instanceof DataMap) {
                value = ((DataMap) value).get(part);
            } else {
                return null;
            }
        }
        return value;
    }
}
