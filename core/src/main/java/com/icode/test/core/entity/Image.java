package com.icode.test.core.entity;

import com.icode.test.core.utils.LinkUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class Image {

    @SlingObject
    private ResourceResolver resourceResolver;

    @Inject
    @Optional
    private String imagePath;

    @Inject
    @Optional
    private String imageTitle;

    public String getImagePath() {
        return LinkUtils.getProperURL(imagePath,resourceResolver);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
}
