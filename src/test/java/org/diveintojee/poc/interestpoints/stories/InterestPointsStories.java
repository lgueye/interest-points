/*
 *
 */
package org.diveintojee.poc.interestpoints.stories;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = false, ignoreFailureInView = false, threads = 2)
@UsingSpring(resources = { "classpath:jbehave-context.xml", "classpath:stories-context.xml" })
public class InterestPointsStories extends InjectableEmbedder {

	/**
	 * @see org.jbehave.core.Embeddable#run()
	 */
	@Test
	@Override
	public void run() throws Throwable {
		injectedEmbedder().runStoriesAsPaths(storyPaths());
	}

	protected List<String> storyPaths() {
		return new StoryFinder().findPaths("src/test/resources", Arrays.asList("**/*.story"), null);
	}
}
