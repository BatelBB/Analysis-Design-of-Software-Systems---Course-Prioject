package adss_group_k.dataLayer.records;

import adss_group_k.dataLayer.records.readonly.CategoryData;

public class CategoryRecord extends BaseRecord<String> implements CategoryData {
    private final String name;

    public CategoryRecord(String name) {
        this.name = name;
    }

    @Override
    public String key() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }
}
