import java.util.Random;


class MyHashTable<K, V> {

    static class HashNode<K, V> {
        K key;
        V value;
        HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private HashNode<K, V>[] Object;
    private int M = 11;
    private int size;

    public MyHashTable() {

        this.Object = new HashNode[M];
        this.size = 0;
    }

    private int hash(K key) {
        int hash = 0;
        String keyStr = key.toString();
        for (int i = 0; i < keyStr.length(); i++) {
            hash += keyStr.charAt(i);
        }
        return hash % M;
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> newNode = new HashNode<>(key, value);
        if (Object[index] == null) {
            Object[index] = newNode;
        } else {
            HashNode<K, V> current = Object[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = Object[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = Object[index];
        HashNode<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    Object[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean contains(K key) {
        int index = hash(key);
        HashNode<K, V> current = Object[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = Object[i];
            while (current != null) {
                if (current.value.equals(value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }

    public int[] getBucketSizes() {
        int[] bucketSizes = new int[M];
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = Object[i];
            int count = 0;
            while (current != null) {
                count++;
                current = current.next;
            }
            bucketSizes[i] = count;
        }
        return bucketSizes;
    }
    public static void main(String [] args) {
        MyHashTable<Integer, Integer> hashTable = new MyHashTable<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int key = random.nextInt(10000);
            int value = random.nextInt(10000);
            hashTable.put(key, value);
        }
        int[] bucketSizes = hashTable.getBucketSizes();
        for (int i = 0; i < bucketSizes.length; i++) {
            System.out.println("Bucket " + i + ": " + bucketSizes[i] + " elements");
        }
    }
}


