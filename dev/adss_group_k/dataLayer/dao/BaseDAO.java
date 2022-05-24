package adss_group_k.dataLayer.dao;

import adss_group_k.dataLayer.records.BaseRecord;
import adss_group_k.shared.response.ResponseT;
import adss_group_k.shared.utils.Tuple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class BaseDAO<TEntityID, TEntity extends BaseRecord<TEntityID>> {
    private HashMap<TEntityID, TEntity> cache;
    private boolean cachedAll;
    protected final Connection conn;

    public BaseDAO(Connection conn) {
        cache = new HashMap<>();
        this.conn = conn;
    }

    public final ResponseT<TEntity> get(TEntityID id) {
        try {
            return ResponseT.success(cache.computeIfAbsent(id, this::fetchWithRuntimeExceptions));
        } catch (Exception e) {
            return ResponseT.error("no such entity");
        }
    }

    public final Stream<TEntity> all() throws SQLException {
        if (!cachedAll) {
            fetchAll().map(t -> new Tuple<TEntity, TEntityID>(t, t.key())).filter(tuple -> !cache.containsKey(tuple.second)).forEach(tuple -> cache.put(tuple.second, tuple.first));
            cachedAll = true;
        }
        return cache.values().stream();
    }

    public boolean exists(TEntityID id) {
        return get(id).success;
    }


    abstract TEntity fetch(TEntityID key) throws SQLException, NoSuchElementException;

    abstract Stream<TEntity> fetchAll() throws SQLException;

    protected final int runUpdate(String statement, StatementInitialization... paramsInit) {
        try {
            PreparedStatement ps = conn.prepareStatement(statement);
            for (StatementInitialization si : paramsInit) {
                si.initialize(ps);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    protected final ResponseT<TEntity> create(Supplier<TEntity> factory, String statement, StatementInitialization... paramsInit) {
        int res = runUpdate(statement, paramsInit);
        if (res > 0) {
            TEntity created = factory.get();
            cache.put(created.key(), created);
            return ResponseT.success(created);
        }
        return ResponseT.error("Error with CREATE");
    }

    protected abstract int runDeleteQuery(TEntityID id);

    public final void delete(TEntityID id) {
        runDeleteQuery(id);
        cache.remove(id);
    }

    private TEntity fetchWithRuntimeExceptions(TEntityID tEntityID) {
        try {
            return fetch(tEntityID);
        } catch (SQLException | NoSuchElementException e) {
            throw new RuntimeException(e);
        }
    }

    public interface StatementInitialization {
        void initialize(PreparedStatement statement) throws SQLException;
    }
}

