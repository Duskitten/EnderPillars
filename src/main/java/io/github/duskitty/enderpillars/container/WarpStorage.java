package io.github.duskitty.enderpillars.container;

import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class WarpStorage
{
	private static HashMap<String, WarpStorage> players = new HashMap<>();

	private List<Warp> warps = new ArrayList<>();

	public List<Warp> getWarps()
	{
		return new ArrayList<>(warps);
	}

	public void setWarps(List<Warp> warps)
	{
		this.warps = new ArrayList<>(warps);
	}

	public Optional<Warp> getWarp(String name)
	{
		return warps.stream().filter(w -> w.getName().equals(name)).findFirst();
	}

	public boolean setWarp(Warp warp)
	{
		String name = warp.getUniqueID();
		int found = IntStream.range(0, warps.size())
			.filter(i -> name.equals(warps.get(i).getUniqueID()))
			.findFirst()
			.orElse(-1);
		if (found == -1)
		{
			warps.add(warp);
			System.out.println(warps.size());
			return false;
		}
		else
		{
			warps.set(found, warp);
			return true;
		}
	}



	public boolean deleteWarp(String name)
	{
		int found = IntStream.range(0, warps.size())
			.filter(i -> name.equals(warps.get(i).getUniqueID()))
			.findFirst()
			.orElse(-1);
		if (found == -1)
		{
			return false;
		}
		else
		{
			warps.remove(found);
			return true;
		}
	}

	public static WarpStorage fromPlayer(ServerPlayerEntity pl)
	{
		WarpStorage storage = players.getOrDefault(pl.getUuidAsString(), null);
		if (storage == null)
		{
			storage = new WarpStorage();
			players.put(pl.getUuidAsString(), storage);
		}
		return storage;
	}

	public static void readNBT(ServerPlayerEntity pl, CompoundTag tag)
	{
		List<Warp> warps = new ArrayList<>();
		ListTag list = (ListTag) tag.get("warpstorage");

		if (list != null)
			for (int i = 0; i < list.size(); i++)
			{
				CompoundTag compound = list.getCompound(i);
				warps.add(new Warp(
						compound.getString("uniqueId"),
					compound.getString("name"),
					compound.getString("dimensionId"),
					compound.getDouble("x"),
					compound.getDouble("y"),
					compound.getDouble("z"),
					compound.getFloat("yaw"),
					compound.getFloat("pitch")));
			}
		fromPlayer(pl).setWarps(warps);
	}

	public static void writeNBT(ServerPlayerEntity pl, CompoundTag tag)
	{
		List<Warp> warps = fromPlayer(pl).getWarps();
		ListTag list = new ListTag();
		for (Warp warp : warps)
		{
			CompoundTag compound = new CompoundTag();
			compound.putString("uniqueId", warp.getUniqueID());
			compound.putString("name", warp.getName());
			compound.putString("dimensionId", warp.getDimensionId());
			compound.putDouble("x", warp.getX());
			compound.putDouble("y", warp.getY());
			compound.putDouble("z", warp.getZ());
			compound.putFloat("yaw", warp.getYaw());
			compound.putFloat("pitch", warp.getPitch());
			list.add(compound);
		}
		tag.put("warpstorage", list);
	}

	public void writeToBuf(PacketByteBuf buf)
	{
		buf.writeInt(warps.size());
		for (Warp warp : warps)
		{
			buf.writeString(warp.getUniqueID());
			buf.writeString(warp.getName());
			buf.writeString(warp.getDimensionId());
			buf.writeDouble(warp.getX());
			buf.writeDouble(warp.getY());
			buf.writeDouble(warp.getZ());
			buf.writeFloat(warp.getYaw());
			buf.writeFloat(warp.getPitch());
		}
	}

	public void readFromBuf(PacketByteBuf buf)
	{
		List<Warp> newwarp = new ArrayList<>();
		warps.clear();
		int length = buf.readInt();
		for (int i = 0; i < length; i++)
		{
			Warp warp = new Warp(
					buf.readString(),
					buf.readString(),
					buf.readString(),
					buf.readDouble(),
					buf.readDouble(),
					buf.readDouble(),
					buf.readFloat(),
					buf.readFloat());
			newwarp.add(warp);
		}
		setWarps(newwarp);
	}

}
