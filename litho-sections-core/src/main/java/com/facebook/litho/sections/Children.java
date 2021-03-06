/*
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.litho.sections;

import static android.support.v4.util.Pools.SynchronizedPool;

import android.support.v4.util.Pools.Pool;
import com.facebook.litho.sections.annotations.GroupSectionSpec;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that represents the children of a {@link GroupSectionSpec}.
 * This is used to mimic component's usage of the {@link Container} class in
 * {@link com.facebook.litho.annotations.LayoutSpec}'s API
 */
public class Children {

  private static final Pool<Builder> sBuildersPool = new SynchronizedPool<>(2);

  private List<Section> mSections;

  private Children() {
    mSections = new ArrayList<>();
  }

  public static Builder create() {
    Builder builder = sBuildersPool.acquire();
    if (builder == null) {
      builder = new Builder();
    }
    builder.init(new Children());

    return builder;
  }

  List<Section> getChildren() {
    return mSections;
  }

  public static class Builder {

    private Children mChildren;

    private Builder() {

    }

    private void init(Children children) {
      mChildren = children;
    }

    public Builder child(Section<?> section) {
      if (section != null) {
        mChildren.mSections.add(section);
      }

      return this;
    }

    public Builder child(Section.Builder<?, ?> sectionBuilder) {
      if (sectionBuilder != null) {
        mChildren.mSections.add(sectionBuilder.build());
      }

      return this;
    }

    public Children build() {
      Children children = mChildren;
      mChildren = null;
      sBuildersPool.release(this);

      return children;
    }
  }
}
