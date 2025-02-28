<script lang="ts">
	import { onMount } from 'svelte';
	import type { Account } from '$lib';

	let accounts: Account[] = $state([]);
	let loading: boolean = $state(true);

	const fetchAccounts = async () => {
		const res = await fetch('http://localhost:8080/api/accounts'); // Adjust URL based on backend
		loading = false;
		accounts = await res.json();
	};

	onMount(fetchAccounts);
</script>

<h1 class="font-title pb-4 text-4xl">Accounts</h1>
<div class="font-body flex flex-col gap-4">
	{#if !loading}
		{#each accounts as account}
			<a href="/accounts/{account.id}" class="flex cursor-pointer gap-4 hover:text-gray-500">
				<div class="flex-1">{account.name}</div>
				<div class:text-red-500={account.balance < 0} class:text-green-500={account.balance > 0}>
					${account.balance}
				</div>
			</a>
		{/each}
		<button class="cursor-pointer p-2 text-gray-800 hover:bg-gray-800 hover:text-white">
			+ Add account
		</button>
	{:else}
		<div>loading...</div>
	{/if}
</div>
