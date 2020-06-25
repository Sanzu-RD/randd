package com.souchy.randd.jade.matchmaking;

public enum GameQueue {
	
	blind {
		@Override
		public Class<QueueeBlind> getQueueeClass() {
			return QueueeBlind.class;
		}

		@Override
		public QueueeBlind createQueuee() {
			return new QueueeBlind();
		}
		
	},
	draft {
		@Override
		public Class<QueueeDraft> getQueueeClass() {
			return QueueeDraft.class;
		}

		@Override
		public QueueeDraft createQueuee() {
			return new QueueeDraft();
		}
	},
	mock {
		@Override
		public Class<QueueeMock> getQueueeClass() {
			return QueueeMock.class;
		}

		@Override
		public QueueeMock createQueuee() {
			return new QueueeMock();
		}
	}
	;
	
	public abstract Class getQueueeClass();
	public abstract <T extends Queuee> T createQueuee();
	
	
}
