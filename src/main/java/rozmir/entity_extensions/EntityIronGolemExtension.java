package rozmir.entity_extensions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityIronGolem;

public class EntityIronGolemExtension extends EntityIronGolem
{
    private int attackTimer;
    
    public EntityIronGolemExtension(final World world) {
        super(world);
    }
     
    public boolean interact(EntityPlayer player)
	{
	    ItemStack itemStack = player.inventory.getCurrentItem();

	    if (itemStack != null)
	    {
	    	if (itemStack.getItem() == Items.iron_ingot && getHealth() < getMaxHealth()) //detect if player is holding healing item
	    	{
	    		if (!player.capabilities.isCreativeMode)
	            {
	                --itemStack.stackSize; //decrease the amount by one
	            }
	    		
	    		heal(4);
	    		
	    		playSound("random.anvil_use", 0.75F, 1.0F); //play healing sound
	    		
	            if (itemStack.stackSize <= 0)
	            {
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }

	            return true;
	        } 
	    }
	    return super.interact(player);    
	}
    
    public boolean canAttackClass(Class entityClass)
    {
    	if (EntityPlayer.class.isAssignableFrom(entityClass) || EntitySnowGolemExtension.class.isAssignableFrom(entityClass))
    	{
    		return false;
    	}
        		
    	else
    	{
    		return super.canAttackClass(entityClass);
    	}
    }
	
}