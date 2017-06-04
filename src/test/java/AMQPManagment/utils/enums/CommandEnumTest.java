package AMQPManagment.utils.enums;

import engineprocessor.core.utils.enums.CommandEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksanderr on 19/03/17.
 */
public class CommandEnumTest {

    @Test
    public void isCommandEnumCanReturnAllValuesAvaliable(){
        Assert.assertEquals(7, CommandEnum.values().length);
        Assert.assertEquals("GO", CommandEnum.values()[0].name());
        Assert.assertEquals("DEBUG", CommandEnum.values()[1].name());
        Assert.assertEquals("QUIT", CommandEnum.values()[2].name());
        Assert.assertEquals("NEW_GAME", CommandEnum.values()[3].name());
        Assert.assertEquals("IS_ENGINE_READY", CommandEnum.values()[4].name());
        Assert.assertEquals("SET_POSITION", CommandEnum.values()[5].name());
        Assert.assertEquals("GET_CHESS_INFORMATION", CommandEnum.values()[6].name());

    }
}
