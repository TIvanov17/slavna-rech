package pu.fmi.slavnarech.configuration;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private static final String HOME_PAGE = "index.html";
  private static final String RESOURCES_PATH = "/static/";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/**")
        .addResourceLocations("classpath:/static/", "classpath:/image/")
        .setCachePeriod(0)
        .resourceChain(false)
        .addResolver(new PushStateResourceResolver());
  }

  private class PushStateResourceResolver implements ResourceResolver {
    private final Resource index = new ClassPathResource("/static/index.html");
    private final List<String> handledExtensions =
        Arrays.asList(
            "html",
            "js",
            "json",
            "csv",
            "css",
            "png",
            "svg",
            "eot",
            "ttf",
            "woff",
            "appcache",
            "jpg",
            "jpeg",
            "gif",
            "ico",
            "pdf",
            "xml");
    private final List<String> ignoredPaths = Arrays.asList("api/");

    @Override
    public String resolveUrlPath(
        String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
      Resource resolvedResource = resolve(resourcePath);

      if (resolvedResource == null) {
        return null;
      }

      try {
        return resolvedResource.getURL().toString();
      } catch (IOException e) {
        return resolvedResource.getFilename();
      }
    }

    @Override
    public Resource resolveResource(
        HttpServletRequest request,
        String requestPath,
        List<? extends Resource> locations,
        ResourceResolverChain chain) {
      return resolve(requestPath);
    }

    private Resource resolve(String requestPath) {
      if (isIgnored(requestPath)) {
        return null;
      }
      if (isHandled(requestPath)) {
        Resource finalLocation = new ClassPathResource(RESOURCES_PATH + requestPath);
        if (finalLocation.exists()) {
          return finalLocation;
        } else {
          return null;
        }
      }
      return index;
    }

    private boolean isIgnored(String path) {
      for (String ignorePath : ignoredPaths) {
        if (path.contains(ignorePath)) {
          return true;
        }
      }

      return false;
    }

    private boolean isHandled(String path) {
      if (HOME_PAGE.equals(path)) {
        return false;
      }

      String extension = StringUtils.getFilenameExtension(path);

      for (String handledExtension : handledExtensions) {
        if (handledExtension.equals(extension)) {
          return true;
        }
      }

      return false;
    }
  }
}
