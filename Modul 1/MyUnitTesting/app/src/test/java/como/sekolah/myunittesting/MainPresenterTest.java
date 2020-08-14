package como.sekolah.myunittesting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

//untuk menginisialisasikan framwork Mockito
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

//    untuk membuat objeck mock dan menggantikan objek yang asli
    @Mock
    private MainPresenter presenter;
    private MainView view;

//    fungsinya untuk menginisialisasikan method sebelum melakukan test
    @Before
    public void setUp(){
        view = mock(MainView.class);
        presenter = new MainPresenter(view);
    }

//    diguakan pada method yang akan di test
    @Test
    public void testVolumeWithIntegerInput(){
        double volume = presenter.volume(2, 8, 1);
        assertEquals(16, volume, 0.0000);
    }

    @Test
    public void testVolumeWithDoubleInput(){
        double volume = presenter.volume(2.3, 8.1, 2.9);
//        assertequals adalah fungsi dari jUnit yang brfungsi uuntuk memvalidasi output yang diharapka  dan yang sebenarnya
        assertEquals(54.026999999999994, volume, 0.0001);
    }

    @Test
    public void testVolumeWithZeroInput(){
        double volume = presenter.volume(0, 0, 0);
        assertEquals(0.0, volume, 0.0001);
    }

    @Test
    public void testCalculateVolume(){
        presenter.calculateVolume(11.1,  2.2, 1);
//        bergua untuk memeriksa sebuah method (Mockito) dan any berfungsi untuk mencocokan argument yang fleksible
        verify(view).showVolume(any(MainModel.class));
    }
}