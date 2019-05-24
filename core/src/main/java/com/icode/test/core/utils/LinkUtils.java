package com.icode.test.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.cq.social.community.api.CommunityConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class LinkUtils {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkUtils.class);

    /**
     * The Constant HTML_SUFFIX.
     */
    public static final String HTML_SUFFIX = ".html";

    /**
     * Instantiates a new link utils.
     */
    private LinkUtils() {}

    /**
     * Check a path is external link.
     *
     * @param resourceResolver the resource resolver
     * @param path the path
     * @return true/false
     */
    public static boolean isExternalLink(final ResourceResolver resourceResolver, final String path) {
        if (resourceResolver == null || StringUtils.isBlank(path)) {
            return true;
        }
        Resource resource = resourceResolver.getResource(path);
        return resource == null;
    }

    /**
     * Add Extension.
     *
     * @param url String
     * @param resolver ResourceResolver
     * @return String
     */
    private static String addExtension(final String url, final ResourceResolver resolver) {
        // add the code to split the URL so that .html can be added
        // at proper place and then query string will be appended
        String newURL;
        if (url.contains("?")) {
            String[] urlSplit = url.split("\\?");
            newURL = urlSplit[0] + ".html?" + urlSplit[1];
        } else if (url.contains("#")) {
            String[] urlSplit = url.split("#");
            newURL = urlSplit[0] + ".html#" + urlSplit[1];
        } else {
            newURL = url + HTML_SUFFIX;
        }
        if (StringUtils.isNotBlank(newURL)) {
            newURL = resolver.map(newURL);
        }
        return newURL;
    }

    /**
     * Get Property Url.
     *
     * @param url String
     * @param resolver ResourceResolver
     * @return String
     */
    public static String getProperURL(final String url, final ResourceResolver resolver) {
        String properURL = url;
        // checking for non blank URL or not null request and resource resolver
        if (properURL == null || StringUtils.isBlank(properURL) || resolver == null) {
            return "#";
        }

        // checking for the url under content node
        if (url.startsWith(CommunityConstants.CONTENT_ROOT_PATH) && !url.startsWith(CommunityConstants.DAM_ROOT_PATH)) {
            // when author have added the extension and query parameters in the
            // URL
            if (url.contains(".")) {
                properURL = resolver.map(url);
            } else {
                properURL = addExtension(properURL, resolver);
            }
        }
        return properURL;
    }


    /**
     * Encodes the link.
     *
     * @param path the page path value..
     * @param characterEncoding the encoding value.
     * @return the encoded path.
     * @throws UnsupportedEncodingException if error.
     */
    private static String tryToDecodeUrl(String path, String characterEncoding) throws UnsupportedEncodingException {
        return URLDecoder.decode(path, characterEncoding);
    }

    /**
     * Checks if it is an external link.
     *
     * @param path the page path value.
     * @param resourceResolver a {@link ResourceResolver}.
     * @return {@code true} if it is an external link.
     */
    private static boolean isExternalUrl(String path, ResourceResolver resourceResolver) {
        Resource resource = resourceResolver.resolve(path);
        return ResourceUtil.isNonExistingResource(resource);
    }
}

