package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.model.MenuItem;
import com.akmal.springfoodieappbackend.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * The class represents the service of the {@link com.akmal.springfoodieappbackend.model.MenuItem} entity.
 * Manages the persistence of the entity.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/11/2021 - 8:50 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MenuItemService {

  private final MenuItemRepository menuItemRepository;

  @Transactional
  public void removeImageReferences(String imageId) {
    Objects.requireNonNull(imageId, "Image id cannot be null");
    final Iterable<MenuItem> menuItems = this.menuItemRepository.findAllByThumbnailImageOrFullImageId(imageId);

    for (MenuItem menuItem : menuItems) {
      MenuItem updatedMenuItem = menuItem;
      if (menuItem.getThumbnailImage() != null
              && imageId.equals(menuItem.getThumbnailImage().getId())) {
        updatedMenuItem = updatedMenuItem.withThumbnailImage(null);
      }

      if (menuItem.getFullImage() != null
              && imageId.equals(menuItem.getFullImage().getId())) {
        updatedMenuItem = updatedMenuItem.withFullImage(null);
      }
      this.menuItemRepository.save(updatedMenuItem);
    }
  }
}
