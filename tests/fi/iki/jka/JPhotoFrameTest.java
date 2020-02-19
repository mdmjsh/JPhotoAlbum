package fi.iki.jka;

import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JPhotoFrameTest {
    @Test
    public void startSlideShowCalledWhenASlideShowCMDSentAndNonemptyFiles() throws Exception {
        String files[] = {"1"};
        final AtomicBoolean moo = new AtomicBoolean(false);

        JPhotoFrame testFrame = new JPhotoFrame("newFrame", files) {
            void startSlideShow(JPhotoCollection photos, JPhotoList photoList, int interval) {
                JPhotoShow show = new JPhotoShow(photos, interval, photoList);
                show.setVisible(true);
                moo.set(true);
            }
        };
        ActionEvent newEvent = new ActionEvent(testFrame, 0, "Start Slideshow");

        testFrame.actionPerformed(newEvent);
        assertThat(moo.get(), equalTo(true));
    }

    @Test
    public void startSlideShowNotCalledWhenASlideShowCMDSentAndEmptyFiles() throws Exception {
        String files[] = {};
        final AtomicBoolean moo = new AtomicBoolean(false);

        JPhotoFrame testFrame = new JPhotoFrame() {
            @Override
            void startSlideShow(JPhotoCollection photos, JPhotoList photoList, int interval) {
                JPhotoShow show = new JPhotoShow(photos, interval, photoList);
                show.setVisible(true);
                moo.set(true);
            }

            @Override
            public void setTitle() {

            }
        };
        ActionEvent newEvent = new ActionEvent(testFrame, 0, "Start Slideshow");

        testFrame.actionPerformed(newEvent);
        assertThat(moo.get(), equalTo(false));
    }

    @Test
    public void startSlideShowNotCalledWhenEventIsNotSlideshow() throws Exception {
        String files[] = {};
        final AtomicBoolean moo = new AtomicBoolean(false);
        final AtomicInteger atomicInteger = new AtomicInteger(0);

        JPhotoFrame testFrame = new JPhotoFrame() {
            @Override
            public void tryStartSlideshow(int interval) {
                atomicInteger.set(interval);
            }

            @Override
            public void setTitle() {

            }
        };
        ActionEvent newEvent = new ActionEvent(testFrame, 0, "Start Slideshow");

        testFrame.actionPerformed(newEvent);
        assertThat(moo.get(), equalTo(false));
        assertThat(atomicInteger.get(), equalTo(5000));
    }

    @Test
    public void startSlideShowNotCalledWhenEventIsNotSlideshowWithNonemptyFiles() throws Exception {
        String files[] = {"2"};
        final AtomicBoolean moo = new AtomicBoolean(false);

        JPhotoFrame testFrame = new JPhotoFrame("newFrame", files) {
            @Override
            void startSlideShow(JPhotoCollection photos, JPhotoList photoList, int interval) {
                JPhotoShow show = new JPhotoShow(photos, interval, photoList);
                show.setVisible(true);
                moo.set(true);
            }
        };
        ActionEvent newEvent = new ActionEvent(testFrame, 0, "Start");

        testFrame.actionPerformed(newEvent);
        assertThat(moo.get(), equalTo(false));
    }
}