package org.csu.mypetstoressm.persistence;

import org.csu.mypetstoressm.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> getCategoryList();

    Category getCategory(String categoryId);
}
