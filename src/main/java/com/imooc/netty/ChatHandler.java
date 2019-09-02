package com.imooc.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * 处理消息的handle
 * TextWebSocketFrame：在netty中，适用于为websocket专门处理文本的对象，frame是消息载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	//用于记录和管理所有客户端的channel
	private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

		//获取客户端传输过来的消息
		String content = msg.text();
		System.out.println("接收的数据："+content);

		for (Channel channel:clients){
			channel.writeAndFlush(
					new TextWebSocketFrame("[服务器接收到消息]:"+ LocalDateTime.now()+"接收到消息，" +
							" 消息为："+content));
		}

		//和for循环一致
		//clients.writeAndFlush("[服务器接收到消息：]"+ LocalDateTime.now()+"接收到消息，消息为："+content);
	}

	/**
	 * 当客户端连接服务器后
	 * 获取客户端的channel，放到channelgroup中进行管理
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		clients.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//当触发handlerremove，channelgroup会自动移除客户端channel
		//clients.remove(ctx.channel());
		System.out.println("客户端断开，channel对应长id："+ctx.channel().id().asLongText());
		System.out.println("客户端断开，channel对应短id："+ctx.channel().id().asShortText());
	}
}
